# Backend

As part of our application process, we'd like to see some code you develop so we can have a conversation around it. 

We’re looking for code that is clean, readable, performant, and maintainable. We are most interested in problem solving skills and seeing how you tackle technical challenges that our common in our daily work.

## Requirements (in priority order)
  
We would like you to create a simple RESTful API service that will support an app with the following features:

 - [ ] Create a new invoice. Required fields w/ example:
```
  "customer_email": "john@test.com",
  "customer_name": "John Daly",
  "description": "For services rendered",
  "due_date": "2022-02-10",
  "status": "draft","approved","sent", paid"
  "total": 35000
``` 
 - [ ] Add 1 or many line items to the invoice. Line items may include a breakdown of each thing being invoiced for with an amount
 - [ ] The ability to edit an invoice object if its still in draft
 - [ ] The ability to change the status of an invoice 
 - [ ] View a list of all invoices by status 

### Tips
Your storage can be anything that you wish.
Don't worry about authenication. 

#### Bonus features (always in priority order)

 - [ ] Persist and list event history for an invoice. (ex: created, updated, approved, sent etc...)
 - [ ] Alert when an invoice is late via a webhook

If you have any questions, ask away!

