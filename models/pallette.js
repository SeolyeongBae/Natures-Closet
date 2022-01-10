//mongo db model -> mvc model based
//mongoose object declaration
const { Int32 } = require("mongodb");
var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var palletteSchema = new Schema({
  name: {
    type: String,
    required: true,
  },
  palettename: {
      type: String,
      required: true
  },
  color1: {
      type: String,
      required: true
  },
  color2: {
    type: String,
    required: true
  },
  color3: {
    type: String,
    required: true
  },
  color4: {
    type: String,
    required: true
  },
  color5: {
    type: String,
    required: true
  },
  color6: {
    type: String,
    required: true
  },
  upcolor: {
    type: String,
    required: true
  },
  downcolor: {
    type: String,
    required: true
  }
});

module.exports = mongoose.model('pallette', palletteSchema);