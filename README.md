# Yelp-for-Hotels
Web portal where user can check, give hotel reviews and search for nearby tourist attractions. Technologies used are Frontend - Angular7, Backend - 
Java servlets and jetty server inbuilt, Database - MySQL and developed it on Mircrosoft Azure.

# Features:
I have implemented features which are usually developed for Industry standard web applications. Features like Cookie, Authentication, authorization etc.
| Feature  | Description |
| ------------- | ------------- |
| User Registration  | "Register" button that allows a user to register on the website. You need to check that the password satisfies a set of requirements (the length is between 5 to 10 characters, contains at least one number, one letter and one special character) - check this both on the frontend and on the backend. User's info (username, hashed salt, hashed password+salt) should be stored in the MySQL database table (you should access the database using JDBC as shown in class). Password should be salted and hashed. |
| Login/Logout Session  | Allow a user to login and logout. You need to maintain the session properly. Your code should use JDBC to access the user account info in MySQL database. Maintain the session for all pages.  |
| Search and View Hotels | Allow the user to search hotels by the city and by the keyword contained in the name of the hotel, and the website should show all the hotels in that city that match this keyword. For instance, if the user types "San Francisco" in the "city" field and types "Merid" in the keyword field, your webserver should return information about Le Meridien San Francisco hotel. Keyword can be left blank - if the user selects San Francisco and does not specify the keyword, the website should show all hotels in San Francisco. |
| Hotel Page for each Hotel | When a user clicks on the name of the hotel (see previous feature), you need to show them a webpage for this hotel. The webpage should contain hotel name, address, property and area descriptions, as well as reviews for this hotel. |
| Add Review | Allow a logged in user to add a review for a hotel (they should be able to specify the title and the text). Add this button to the Hotel webpage (see previous line). |
| Modify or Delete Review | Allow a logged in user to edit their review and delete it.  |
|Tourist Attractions | Have an option to display a list of tourist attractions within a certain radius of a given hotel. |
| Store Hotel and Review Info in the Database |  Use MySql database to store hotel and review data. All operations on hotels and reviews should use the database.|
| "Liking" Reviews | Allow users to "like" a review if they find the review helpful. Underneath the review, display a label "n users found this Review helpful" and a "Like" button. A user should be logged in before liking a review, and should be able to like each review only once. |
| Show N reviews per page | Provide pagination: show a fixed number of reviews on each page, the user should be able to navigate through multiple pages with reviews. |
| GoogleMap  | Embed GoogleMaps on your site: -On the main page, show a map of SF bay area, and show all hotels with markers. -On the hotel page, show the hotel on the map (center the map on the hotel). |
| Visited Expedia Links| Store a history of all expedia hotel links visited by a user, and allow the user to view and clear that history. |
| Last Login Time | Store and display the last date&time the user logged in to the website before this time (the feature is available only for logged in users). |


# How to Run
1. Create Mysql DB and load DDL.sql file which will created necessary tables.
2. Run backend server by running \Backend\src\main\java\jettyTravelHelperServer\JettyTravelHelperServer.java file, this will start server on 8090 port.
3. Run UI locally by running command : npm install + npm run start. Currently UI is using proxy file to connect to backend since we can not connect directly due to CORS. UI will load at http://localhost:8080/
4. You can use attached postman collection to run the backend.

# Postman script

You can find the Postman script [here]()

# Home page

![alt text](http://url/to/img.png)
