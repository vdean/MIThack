import webapp2
import jinja2
import logging
import os
import re
from google.appengine.ext import ndb
from google.appengine.api import users
from google.appengine.api import mail
import contact


def GetEvents(usr_api):
    user = Account.query().filter(Account.user==usr_api).get()
    
    return Event.query().filter(Event.user==usr_api)


def RenderTemplate(template_file, values={}):
    template = jinja_environment.get_template(template_file)
    
    return template.render(values)


class Account(ndb.Model): 
    """Added from signup"""
    first_name = ndb.StringProperty(required=True)
    last_name = ndb.StringProperty(required=True)
    email = ndb.StringProperty(required=True)
    friends = ndb.UserProperty(repeated=True)
    photo = ndb.BlobProperty(required=False)
    user = ndb.UserProperty(required=True)
    phonenumber = ndb.IntegerProperty(required=True)


class Event(ndb.Model): 
    """Event created by a person"""
    user = ndb.UserProperty(required=True)
    friends = ndb.UserProperty(repeated=True)
    restaurant = ndb.StringProperty(required=False)
    date = ndb.StringProperty(required=False)


class RegisterHandler(webapp2.RequestHandler): 
    """Add new user"""
    def get(self):
        self.response.out.write(RenderTemplate('signup.html'))
    
    def post(self):
        user = users.get_current_user()
        try:
            new_user = Account(first_name=self.request.get('fn'), 
                           last_name=self.request.get('ln'), 
                           email=user.email(),
                           user=user,
                           photo = self.request.get('img'),
                           phonenumber = int (re.sub('[^0-9]', '', self.request.get('phonenumber'))),)
            new_user.put()
        except:
            pass  
        self.redirect('/')
             
        

class HomeHandler(webapp2.RequestHandler): 
    """The news feed"""
    def get(self):
        friend_events = []
        account = Account.query().filter(Account.user==users.get_current_user()).get()
        if account: 
            my_events = GetEvents(users.get_current_user())
            for person in account.friends:
                friend_events.append(GetEvents(person))
            friend_events.append(my_events)
            self.response.out.write(RenderTemplate('home.html', {'all_events': friend_events, 
                                                                'account': account, 'months': months}))
        else:
            self.response.out.write(RenderTemplate('signup.html'))

    def post(self):
        if self.request.get('request_id') == 'add_friend':
            email = self.request.get('email') 
            user = Account.query().filter(Account.user==users.get_current_user()).get()
            friend = users.User(email)
            if friend not in user.friends:
                user.friends.append(friend) 
            user.put()

        elif self.request.get('request_id') == 'add_person':
            event_ID = int(self.request.get('event_ID'))
            addee_ID = int(self.request.get('addee_ID'))
            event = Event.get_by_id(event_ID)
            addee = Account.get_by_id(addee_ID)
            owner = Account.query().filter(Account.user==event.user).get()
            if addee.user not in event.friends:
                event.friends.append(addee.user)
                SendText(owner.phonenumber, addee.first_name)
            event.put()
        
        self.redirect('/')
      
class AboutHandler(webapp2.RequestHandler):
    def get(self):
        self.response.out.write(RenderTemplate('about.html'))

class DeveloperHandler(webapp2.RequestHandler):
    def get(self):
        self.response.out.write(RenderTemplate('developers.html'))

class ProfileHandler(webapp2.RequestHandler): 
    """A bio page"""
    def get(self):
        usr = users.get_current_user()
        events = GetEvents(usr)     
        logging.info(events)
        account = Account.query().filter(Account.user==usr).get()
        self.response.out.write(RenderTemplate('profile.html', {'events': events, 'account': account, 'months': months}))

    def post(self):
        restaurant = self.request.get('restaurant')
        date_time = self.request.get('date')
        user_event = Event(user=users.get_current_user(), 
                           restaurant=restaurant, 
                           date=date_time)
        user_event.put()
        self.redirect('/profile')
        
class ImageHandler(webapp2.RequestHandler):
    def get(self):
        message_id = self.request.get('img_id')
        message = Account.get_by_id(int(message_id))
        if message.photo:
            self.response.headers['Content-Type'] = 'image/jpg'
            self.response.out.write(message.photo)
        else:
            self.response.out.write("No Image")

class ContactHandler(webapp2.RequestHandler): 
    def get(self):
        template_values = {}
        template = jinja_environment.get_template('contact.html')
        self.response.out.write(template.render(template_values))

    def post(self):
        FirstName = self.request.get('FirstName')
        LastName= self.request.get('LastName')
        Comment= self.request.get('comment')
        template_values = {}
        template = jinja_environment.get_template('contact.html')
        self.response.out.write(template.render(template_values))
        message = mail.EmailMessage(sender="noreply@meet-munch.appspotmail.com",
                                    subject="Feedback")
        message.to = "bryan3509@gmail.com"
        message.body = FirstName+ ' ' + LastName + ' '  + 'says: ' + Comment
        message.send()
        self.redirect('/contact')

routes = [
    ('/', HomeHandler),
    ('/profile', ProfileHandler),
    ('/register', RegisterHandler),
    ('/img', ImageHandler),
    ('/aboutus', AboutHandler),
    ('/contact', ContactHandler),
    ('/developers', DeveloperHandler)
]

app = webapp2.WSGIApplication(routes, debug=True)
 