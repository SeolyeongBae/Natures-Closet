//mongo db model -> mvc model based
//mongoose object declaration
const mongoose = require("mongoose"),
userSchema = mongoose.Schema({
    name: String,
    id: String,
    pwd: String
  });
module.exports = mongoose.model("User",userSchema);