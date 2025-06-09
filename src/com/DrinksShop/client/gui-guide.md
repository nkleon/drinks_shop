# Building the GUI for our app

Use JavaFX for the GUI. Download SceneBuilder from <https://gluonhq.com/products/scene-builder/#download> and install to make it easier to edit the layout of the controls. 

Create the FXML files and controller classes in `client` package.

## User interfaces we need to make

### User functionality

+ Customer login - should also have option for customer to select the branch they are at
+ Customer registration - customer should be able to add email, 1 name, phone and password
+ Customer homepage
+ Making an order - customer should select drink to order and the quantity

### Admin functionality

+ Admin login
+ Admin homepage
+ Stock levels report - should have a table with columns: branch name, drink name and stock quantity
+ Orders report - should have a table with columns: order id, order date, branch name, customer name, drink name, quantity, price
+ Sales by branch report - should have a table with columns: branch id, branch name, total quantity ordered, total sales
+ Sales by customer report - should have a table with columns: customer id, customer name, total quantity bought , total purchases
+ Sales by drink report - should have a table with columns: drink id, drink name, total quantity ordered, total sales
+ Edit stock levels - admin should be able to edit stock of a particular drink for a particular branch

