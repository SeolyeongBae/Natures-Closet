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

var Posts = require('../models/post');

router.post('/share', function(req, res) {
    var localName = req.body.username;
    var localColor1 = req.body.color1;
    var localColor2 = req.body.color2;
    var localColor3 = req.body.color3;
    var localColor4 = req.body.color4;
    var localColor5 = req.body.color5;
    var localColor6 = req.body.color6;
    var localContents = req.body.contents;
    var localHashtag = req.body.hashtag;

    Posts.create(
        {
            name:localName,
            color1:localColor1,
            color2:localColor2,
            color3:localColor3,
            color4:localColor4,
            color5:localColor5,
            color6:localColor6,
            contents:localContents,
            hashtag:localHashtag

        },
        function (error, savedDocument) {
            if (error) console.log(error);
            console.log(savedDocument);
            console.log("posting success!");
          }
    );

});

//db에 저장된 shared object를 '/sharepost' 요청을 받으면 클라이언트로 전송
router.post('/sharepost', function(req, res) {
    var localName = req.body.username; //피드를 보여줄 username은 알고 있어야 함

    Posts.find( function(err,docs) {
        console.log("I got sharepost request");
        if (err) {
            console.log(err);
            return res.json({ 'status': 'false', 'msg': 'error occurred', 'data':'nodata' });
        }
        else {
            console.log("sharepost request success!");
            console.log(docs)
            return res.json({ 'status': 'true', 'msg': 'results:', 'data': docs });
        }
    });

});

module.exports = router;