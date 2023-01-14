Unit 8: Book Club - CodePath Android Course 2021 Group 12 (NJIT)
===

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Schema](#Schema)

## Overview
### Description
This Android application allows users to interact on a social environment by creating book clubs and discussing books. Users can add and remove books and can communicate with other users to discuss the contents of the book and questions related to the book.

### App Evaluation
- **Category:** Social Networking / Literature
- **Mobile:** This app would be primarily developed for mobile but would perhaps be just as viable on a computer, such as tinder or other similar apps. Functionality wouldn't be limited to mobile devices, however mobile version could potentially have more features.
- **Story:** Analyzes users book choices, and connects them to other users with similar choices. The user can then decide to message this person via a group.
- **Market:** Any individual could choose to use this app, and to keep it a safe environment, people would be organized into age groups.
- **Habit:** This app could be used as often or unoften as the user wanted depending on how deep their social life is, and what exactly they're looking for.
- **Scope:** First the user would be able to create or join a group depending their interests. The owner of the group can modify the group to the description of their liking. 

## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [x] User can login and logout of his or her account
- [x] Logout Button takes you to the welcome screen
- [x] When you are in the Login Screen, you can click text that says Register to bring you to the Register Screen
- [x] When you are in the Register Screen, you can click text that says Login to bring you to the login screen
- [x] Email must have “@“ in it, gives alert to user if it is not and does not make account
- [x] If an account with similar credentials is already made, it won't make the account 
- [x] Connected data to back4app
- [x] The current signed in user is persisted across app restarts.
- [X] The User could switch between different tabs
- [x] User should be able to edit predefined profile information (preferred genre section)
- [X] User should have the option to view/add friends
- [X] User should be able to edit/create groups (book clubs) as well as add people from their friends list
- [X] Groups have a real time discussion pages
- [X] Direct messaging support (direct 1 on 1), messages are saved in the chats

**Optional Nice-to-have Stories**

- [x] Can make password hidden or visible when signing in by clicking on the eye icon
- [x] Password has to be at least 7 characters long, gives alert to user if it is not and does not make account
- [ ] User sees app icon in home screen and styled bottom navigation view
- [ ] Style the feed to look like the real book club page
- [ ] User/ Group Profiles
      - Allow the logged in user to add a profile photo
      - Display the profile photo with each post

### App Walkthough GIF


<img src="https://github.com/CodePath-Book-Club/project/blob/main/BookClub_finalwalkthrough.gif" title='Video Walkthrough' width='300' alt='Video Walkthrough' />


### 2. Screen Archetypes

* Login 
* Register - User signs up or logs into their account
   * Upon Download/Reopening of the application, the user is prompted to log in to gain access to their profile information to be properly matched with another person. 
* Messaging Screen - Chat for users to communicate
   * Upon selecting desired group, users matched and message screen opens
* Profile Screen 
   * Allows user to upload a photo and fill in information that is interesting to them and others, show active groups/ current books, and shows upcoming groups meetings
* Friends Screen.
   * Shows the user's friends list and have an option to add more friends.
* Discussion Screen
   * Allows group members to discussion contents of the book.
* Home Screen
   * Allows users to find/browse through different book club groups

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Login/Registration
* Profile
* Messaging 
* Friends
* Home Screen  
* Groups 


Optional:
* Settings
* Discover section

**Flow Navigation** (Screen to Screen)
* Forced Log-in -> Account creation if no log in is available
* Profile -> Log in screen. 
* Any Screen -> Any other screen using the navigation tool (Groups, Home Screen, Friends, Messaging)
* Friend Screen -> Individual profile
* Group Screen -> Discussion Page
* Group Screen -> Create a group page

## Wireframes
[Imgur](https://imgur.com/nPqubEB)

### [BONUS] Digital Wireframes & Mockups
[Imgur](https://imgur.com/D2V0gaM)

### [BONUS] Interactive Prototype
[Imgur](https://imgur.com/V8l4NJ3)

## Schema 
### Models
#### Post

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | objectId      | String   | unique id for each of the book club groups (default field) |
   | author        | Pointer to User| image author |
   | image         | File     | cover image of the book that user posts |
   | caption       | String   | review by the user |
   | commentsCount | Number   | number of comments that has been posted to a book |
   | likesCount    | Number   | number of likes for the post |
   | createdAt     | DateTime | date when post is created (default field) |
   
   #### Profile
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | username       | Pointer to User| image author |
   | userImage         | File     | profile picture |
   | descriptionCaption       | String   | profile description of the author |
   | postCount | Number   | number of posts made by the user |
   | groupCount    | Number   | number of groups that the user is in |
   | friendList    | Array   | friends that the user can interact with |
   
   #### Book Club Group
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for each book club group (default field) |
   | groupCreater        | Pointer to User| moderator of the group |
   | image         | File     | image of the book |
   | caption       | String   | caption states what the book club is about |
   | groupName       | String   | name of the book club |
   | commentsCount | Number   | number of comments that has been posted to an image |
   | UserCount    | Number   | number of users in the club |
   | createdAt     | DateTime | date when post is created (default field) |
   
### Networking
#### List of network requests by screen
   - Home Feed Screen
      - (Read/GET) Query all posts where user is author
         ```swift
         let query = PFQuery(className:"Post")
         query.whereKey("author", equalTo: currentUser)
         query.order(byDescending: "createdAt")
         query.findObjectsInBackground { (posts: [PFObject]?, error: Error?) in
            if let error = error { 
               print(error.localizedDescription)
            } else if let posts = posts {
               print("Successfully retrieved \(posts.count) posts.")
           // TODO: Do something with posts...
            }
         }
         ```
      - (Create/POST) Create a new review for a book
      - (Delete) Delete existing review
      - (Create/POST) Create a new comment on a user's post
      - (Delete) Delete existing comment
   - Create Group Screen
      - (Create/POST) Create a new post object
   - Create Post Screen
      - (Create/POST) Create a new post object
   - Profile Screen
      - (Read/GET) Query logged in user object
      - (Update/PUT) Update user profile 
