const Pesanan = require("../models/pesanan.model.js");

// Create and Save a new Customer
exports.create = (req, res) => {
  // Validate request
  if (!req.body) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
  }

  // Create a Customer
  const pesanan = new Pesanan({
    merk: req.body.merk,
    catatan: req.body.catatan,
    userid: req.body.userid,
    biaya: req.body.biaya,
    jenis: req.body.jenis,
  });
console.log('data ', pesanan)
  // Save Customer in the database
  Pesanan.create(pesanan, (err, data) => {
    if (err)
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the Customer."
      });
    else res.send(data);
  });
};

// Retrieve all Customers from the database.
exports.findAll = (req, res) => {
    Pesanan.getAll((err, data) => {
        if (err)
          res.status(500).send({
            message:
              err.message || "Some error occurred while retrieving customers."
          });
        else res.send(data);
      });
};

exports.findOne = (req, res) => {
  Pesanan.findById(req.params.customerId, (err, data) => {
    if (err) {
      if (err.kind === "not_found") {
        res.status(404).send({
          message: `Not found Pesanan with id ${req.params.customerId}.`
        });
      } else {
        res.status(500).send({
          message: "Error retrieving Pesanan with id " + req.params.customerId
        });
      }
    } else res.send(data);
  });
};