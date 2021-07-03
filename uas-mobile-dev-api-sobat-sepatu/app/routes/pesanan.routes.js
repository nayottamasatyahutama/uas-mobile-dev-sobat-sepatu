module.exports = app => {
    const pesanans = require("../controllers/pesanan.controller.js");
  
    // Create a new Customer
    app.post("/pesanan", pesanans.create);
  
    // Retrieve all Customers
    app.get("/pesanan", pesanans.findAll);
  
    // Retrieve a single Customer with customerId
    app.get("/pesanan/:customerId", pesanans.findOne);
  };