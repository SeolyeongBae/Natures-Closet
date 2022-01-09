var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
const app = require('../app');

mongoose.connect(
  "mongodb://localhost:27017/board",
  {useNewUrlParser: true}
);

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
var Users = require('../models/user');

/* Andorid에서 신규 회원 정보를 json 형식 으로 보내면 MongoDB로 Insert 함.
/* Andorid code 부분 하단에 기술 */
router.post('/join', function(req, res) {
  var localEmail = req.body.userid;
  var localPassword = req.body.userpw;
  var localName = req.body.username;
  
  Users.create(
    {
      email: localEmail,
      password: localPassword,
      name: localName
    },
    function (error, savedDocument) {
      if (error) console.log(error);
      console.log(savedDocument);
      console.log("sign up success!")
    }
  );

});

router.post('/login', function(req, res) {
  console.log('I got login request!');

  //id confirm
  Users.findOne({ email: req.body.userid, password: req.body.userpw }, (err, user) => {
    if (err) {
      //console.log("error occurred while logging in.");
      return res.json({ 'status': 'error', 'msg': 'error!', 'data':'nodata' });
     }
    else if (user) {
      //console.log(user);
      return res.json({ 'status': 'true', 'msg': 'finding user!', 'data': user});
    }
    else {
      //console.log("no user in database");
      return res.json({ 'status': 'false', 'msg': 'no user!', 'data':'nodata' });
    }
  });
});

module.exports = router;