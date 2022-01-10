//mongo db model -> mvc model based
//mongoose object declaration
var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var postSchema = new Schema({
  email: {
    type: String,
    required: true,
    unique: true
  },
  password: {
    type: String,
    required: true
  },
  name: {
    type: String,
    required: true
  }
});

module.exports = mongoose.model('user', postSchema);