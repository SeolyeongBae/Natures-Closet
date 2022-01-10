//mongo db model -> mvc model based
//mongoose object declaration
var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var userSchema = new Schema({
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
    required: true,
    unique: true
  },
  profile: {
    type: String
  }
  /*
  userinfo: {
    type: [{
      color1: 
      color2:
      color3:
      color4:
      color5:
      color6:
      picture: 
    }]
  }

  */
  //making below by using a list type 
  //color field 1~6
  //profile figure
  //
});

module.exports = mongoose.model('user', userSchema);