# Natures-Closet

<img width="99" alt="logo" src="https://user-images.githubusercontent.com/80435616/148944091-9a616756-a924-456c-951a-5589dc431872.png">

“**자연의 색을 그대 옷장에 넣어볼까요”**

**팔레트** 추출 기능을 기반으로 한 **옷 배색 테스트** 및 **공유** 서비스입니다.

---

**Tech stack**

- MongoDB
- Nodejs
- Kotlin

---

## 1. Database / MongoDB

- users - 사용자 정보 테이블
    - Field: email, password, name, profile
    - email, password 정보를 입력받아 name & profile 정보 보여줌
- posts - 피드에 올라갈 정보에 대한 테이블
    - Field: name, color 1~6, contents, hashtag
    - Feed에 입력한 정보를 바탕으로 DB를 구성함
    - name: key 값으로 사용
- palettes - 맛집 이미지 테이블
    - Field: name, palettename, color 1~6, upcolor, downcolor
    - name을 key 값과 같이 사용하고, 나머지 정보를 post로 호출해 사용

## 2. Server / Nodejs

- Nodejs express 모듈 사용

### 클라이언트 요청

```
//로그인 요청
@POST("/login/")
    fun requestLogin(
        @Field("userid") userid:String,
        @Field("userpw") userpw:String
    ) : Call<LoginResponse> 

//회원가입 요청
@POST("/join/") 
    fun requestLogin(
        @Field("userid") userid:String,
        @Field("userpw") userpw:String,
        @Field("username") username:String,
        @Field("profile") profile:String
    ) : Call<JoinResponse> 

//프로필 변경 시 변경사항을 서버에 요청
@POST("/profedit/") 
    fun requestEdit(
        @Field("username") username:String,
        @Field("profile") profile:String
    ) : Call<EditResponse>

//피드를 불러오는 요청
@FormUrlEncoded
    @POST("/sharepost/") 
    fun requestFeed(
        @Field("username") username:String
    ) : Call<FeedResponse> 

//프로필을 불러오는 요청
@FormUrlEncoded
    @GET("posts")
    fun getProfile(@Query("userId") userId: Int): Call<LoginResponse>

//저장한 팔레트 조회 요청
@FormUrlEncoded
    @POST("/show/") 
    fun requestPalette(
        @Field("username") username:String
    ) : Call<PaletteResponse>

//팔레트 제작 후 저장 요청
@POST("/save/") 
    fun requestLogin(
        @Field("username") username:String,
        @Field("palettename") palettename:String,
        @Field("color1") color1:String,
        @Field("color2") color2:String,
        @Field("color3") color3:String,
        @Field("color4") color4:String,
        @Field("color5") color5:String,
        @Field("color6") color6:String,
        @Field("upcolor") upcolor:String,
        @Field("downcolor")downcolor:String,
    ) : Call<LoginResponse> 

//팔레트를 공유할 때 게시글을 피드에 올리는 요청
@FormUrlEncoded
    @POST("/share/") 
    fun requestShare(
        @Field("username") username:String,
        @Field("color1") color1:String,
        @Field("color2") color2:String,
        @Field("color3") color3:String,
        @Field("color4") color4:String,
        @Field("color5") color5:String,
        @Field("color6") color6:String,
        @Field("contents") contents:String,
        @Field("hashtag") hashtag:String
    ) : Call<LoginResponse> 
```

### (1) 기본 설정

### 데이터베이스 연결

```
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

```

### 라우팅 설정

```
var userRouter = require('./routes/user');
var postRouter = require('./routes/post');
var palletteRouter = require('./routes/pallette')

세 개의 db에 연결해줌
```

### (2) memeber

- POST 전화번호
- POST 사용자 정보
- POST 사용자 프로필 이미지
- POST 피드 정보

### Collection 1: User

- URL을 통한 요청은 @Field로 요청
- API 구성: 회원가입을 위한 join, 프로필 사진 수정을 위한 profedit, 그리고 로그인 요청을 위한 login 으로 구성되어 있다.
- 셋 다 POST 방식으로 작동한다. join에서는 email, password, name, profile 정보를 읽어와서 새로운 document를 생성한다. profedit에서는 profile 사진을 변경한다. 마지막으로 login에서는 email, password 값에 맞는 데이터가 DB내에 존재하는지 확인하고 반환한다.

```
router.post('/join', function(req, res) {
  var localEmail = req.body.userid;
  var localPassword = req.body.userpw;
  var localName = req.body.username;
  var localProfile = req.body.profile;
  
  Users.create(
    {
      email: localEmail,
      password: localPassword,
      name: localName,
      profile: localProfile
    },
    function (error, savedDocument) {
      if (error) console.log(error);
      console.log(savedDocument);
      console.log("sign up success!");
    }
  );

});

router.post('/profedit', function(req, res) {
  var localName = req.body.username;
  var localProf = req.body.userprof;

  Users.findOneAndUpdate({ name: localName }, { profile: localProf }, {
    new: true
  }, function (err,result) {
    console.log("I got modify request");
    if (err) console.log(err);
    else console.log(result);
  });
});

router.post('/login', function(req, res) {
  console.log('I got login request!');

  //id confirm
  Users.findOne({ email: req.body.userid, password: req.body.userpw }, (err, user) => {
    if (err) {
      return res.json({ 'status': 'error', 'msg': 'error!', 'data':'nodata' });
     }
    else if (user) {
      console.log(user);
      return res.json({ 'status': 'true', 'msg': 'finding user!', 'data':[user.name, user.profile]});
    }
    else {
      return res.json({ 'status': 'false', 'msg': 'no user!', 'data':'nodata' });
    }
  });

}
```

### Collection 2: Posts

- API 구성: Feed 생성을 위한 share, Feed 공유를 위한 sharepost로 구성되어 있다.

```
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

```

### Collection 3: Palettes

- API 구성: palette instance 생성을 위한 save, my palette를 보기 위한 show로 구성되어 있다.

```
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

```


## 3. Client / Android

 **1. Flowchart**
앱의 구조를 담은 flowchart다.     
<img src="https://user-images.githubusercontent.com/80435616/148944367-dcfb1f50-30d8-4e7a-951f-848e81764b59.png">
    

 **2. LoginActivity & RegisterActivity**

LoginActivity : 기존 등록된 정보로 Login한다. Sign up을 누르면 RegisterActivity로 이동한다.

RegisterActivity : 회원가입을 할 수 있다. Username, Userid, Userpwd를 입력해서 가입한다.

<img width="250" alt="logo" src="https://user-images.githubusercontent.com/80435616/148944507-97bbde0d-051a-4b13-b56d-87f8b9f96c37.png"><img width="250" alt="logo" src="https://user-images.githubusercontent.com/80435616/148944510-27847863-986f-43ad-ab4d-f1adf03ce7b6.png">

 **3. TAB 1: Profile**

프로필 사진, 유저 닉네임, 유저가 제작한 팔레트 및 코디가 보인다. 

- 프로필 사진 수정 기능 : 처음 로그인 시 기본 프로필 사진으로 세팅되어 있다. 프로필 사진 변경을 누르면 사진이 변경된다.
- SHARE 기능 : 유저가 제작한 팔레트를 공유할 수 있다. 공유하지 않은 팔레트는 유저 프로필에 담겨 있다. recyclerview에 담겨 있는 item을 누르면 PostActivity로 넘어간다.
- PostActivity에서는 글의 제목, 글에 넣을 Hashtag을 작성할 수 있다. 우측 상단의 화살표를 누르면 share 할 수 있다.

<img width="250" alt="logo" src="https://user-images.githubusercontent.com/80435616/148945684-53061299-62a0-40f9-9a4f-990f8a9fe6cd.gif">

 **4. TAB 2: Today’s Color**
- 탭 상단 ’Hello’ 다음 유저 닉네임이 표시된다.
- Profile 탭에서 유저들이 공유했던 팔레트 색상이 풍선 위에 입혀져 표시된다.
- Instagram과 유사하게 유저명, 게시글 내용, 해시태그가 표시된다.
- Heart를 누르면 lottie 애니메이션이 재생되면서 숫자가 증가한다.

<img width="250" alt="logo" src="https://user-images.githubusercontent.com/80435616/148945690-598a3814-3c9c-43b7-8cd4-a38312e60587.gif">

 **5. TAB 3: Palette** 
- Palette API 라이브러리를 사용해서 사진에서 대표 색 6개를 추출한 뒤 화면에 표시한다.
- 하단 사진 추가 버튼을 누르면 갤러리에서 원하는 사진을 가져올 수 있다.
- Drag&Drop을 사용했다. 띄워진 color를 가져가서 옷 위에 drop하면 옷에 색상이 입혀진다.
- 상단에 palette의 이름을 입력할 수 있다.
- Save를 누르면 입은 옷의 색, 팔레트의 색이 저장되고 이는 profile에서 조회할 수 있다.

<img width="250" alt="logo" src="https://user-images.githubusercontent.com/80435616/148945698-f6f5b87e-cec7-4788-8361-db3cf91b8faf.gif"><img width="250" alt="logo" src="https://user-images.githubusercontent.com/80435616/148945699-d1be200a-80e0-4ad3-8a43-92232463a18a.gif"><img width="250" alt="logo" src="https://user-images.githubusercontent.com/80435616/148945693-4d8d5a6f-f0e2-492d-b42a-0f60cd27b1b5.gif">


---
## Contacts
박종준 pjjkey@kaist.ac.kr
배설영 seolyeongbae@gm.gist.ac.kr
