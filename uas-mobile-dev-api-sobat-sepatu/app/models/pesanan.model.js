const sql = require("./db.js");

// constructor
const Pesanan = function(pesanan) {
  this.merk = pesanan.merk;
  this.catatan = pesanan.catatan;
  this.userid = pesanan.userid;
  this.biaya = pesanan.biaya;
  this.jenis = pesanan.jenis;
};

Pesanan.create = (newPesanan, result) => {
  sql.query("INSERT INTO pesanan SET ?", newPesanan, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    console.log("created pesanan: ", { id: res.insertId, ...newPesanan });
    result(null, { id: res.insertId, ...newPesanan });
  });
};

Pesanan.findById = (customerId, result) => {
  sql.query(`SELECT * FROM pesanan WHERE userid = '${customerId}'`, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    if (res.length) {
      console.log("found pesanan: ", res);
      result(null, res);
      return;
    }

    // not found Customer with the id
    result({ kind: "not_found" }, null);
  });
};

Pesanan.getAll = result => {
  sql.query("SELECT * FROM pesanan", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log("pesanan: ", res);
    result(null, res);
  });
};

module.exports = Pesanan;