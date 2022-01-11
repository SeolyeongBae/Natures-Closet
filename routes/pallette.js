var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
const app = require('../app');

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
    console.log('connected successfully');
});


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});


/* POST home page. */
/*

*/
var Palettes = require('../models/pallette');

router.post('/save', function(req, res) {
    var localName = req.body.username;
    var localPalname = req.body.palettename;
    var localColor1 = req.body.color1;
    var localColor2 = req.body.color2;
    var localColor3 = req.body.color3;
    var localColor4 = req.body.color4;
    var localColor5 = req.body.color5;
    var localColor6 = req.body.color6;
    var localUpcol = req.body.upcolor;
    var localDowncol = req.body.downcolor;

    Palettes.create(
        {
            name: localName,
            palettename: localPalname,
            color1: localColor1,
            color2: localColor2,
            color3: localColor3,
            color4: localColor4,
            color5: localColor5,
            color6: localColor6,
            upcolor: localUpcol,
            downcolor: localDowncol
        },
        function (error, savedDocument) {
            console.log("I got save request");
            if (error) console.log(error);
            console.log(savedDocument);
            console.log("posting success!");
          }
    );

});

router.post('/show', function(req,res) {
    var localUser = req.body.username;
    console.log(localUser);

    Palettes.find( {name: localUser}, 'palettename color1 color2 color3 color4 color5 color6 upcolor downcolor -_id', 
        function(err,docs) {
            console.log("I got show request");
            if (err) {
                console.log(err);
                return res.json({ 'status': 'false', 'msg': 'error occurred', 'data':'nodata' });
            }
            else {
                console.log("request success!");
                console.log(docs)
                return res.json({ 'status': 'true', 'msg': 'results:', 'data': docs });
            }
        });
});

module.exports = router;