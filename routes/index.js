var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');


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

/* Andorid에서 신규 회원 정보를 json 형식 으로 보내면 MongoDB로 Insert 함.
/* Andorid code 부분 하단에 기술 */
router.post('/join', function(req, res) {
   console.log('I got request!');
    console.log(req.body);
   db.collection('user').insertOne(req.body); // name : 해당 DB에서 사용할 collection(table) 명
   res.end();
});

module.exports = router;