
## Level 2 - task 6
> a1qa study project

Testing subscribing to [euronews.com](https://www.euronews.com/newsletters) newsletters, in communication with the Gmail API. This taks had an additional catch - we could **not** use *Google API Client Library* to obtain OAuth 2.0 authorization, this had to be done according to [these steps](https://developers.google.com/identity/protocols/oauth2/web-server?hl=en#obtainingaccesstokens).
Libraries used in this project: (aquality) Selenium, TestNG, REST Assured, jsoup. 

##### preconditions
 - Obtain access token for the Gmail API. 
 - Mark all emails in the inbox as read.
<br>

#### test case - subscribe and unsubscribe from newsletter
|  step | expected result  |
| ------------ | ------------ |
| Go to [euronews.com](https://www.euronews.com) page | Main page is displayed |
| Accept cookies <br>Click on *newsletter* link | Newsletter page is displayed |
| Select a random newsletter to subscribe to | Newsletter subscription form appeared on the bottom of the page |
| Enter email into the form; <br>Click Submit button | A new email arrived, with a request to confirm subscription | 
| Open the url from the mail, to confirm subscription | Subscription confirmation page was displayed |
| Click *Back to the site* button | Main page is displayed |
| Go to newsletter page; <br>Open preview of the subscribed newsletter | Newsletter preview is open |
| Open unsubscribe url from the newsletter preview | Unsubscribe page is displayed |
| Enter email into the form; <br>Click unsubscribe button | A new email arrived with unsubscription confirmation|


