//mongo db model -> mvc model based
//mongoose object declaration
var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var userSchema = new Schema({
  email: String,
  localPassword: String,
  name: String,
  jsonWebToken: String
});

module.exports = mongoose.model('user', userSchema);