const express = require('express');
const path = require('path');
const dotenv = require('dotenv');
const morgan = require('morgan')
const cookieParser = require('cookie-parser');
const session = require('express-session')

// .env 파일 읽어서 process.env로 만듦
// preocess.env.COOKIE_SECRET에 cookiesecret값 할당
// process.env 별로 관리 이유 : 보안과 설정 편의
// .env 파일 만들어서 비밀 키 적어두고 dotenv 패키지로 비밀키 로딩
dotenv.config();
const indexRouter = require('./routes');
const userRouter = require('./routes/user');
const fileRouter = require('./routes/file');
const loginRouter = require('./routes/login');
const logoutRouter = require('./routes/logout');
const joinRouter = require('./routes/join');

const app = express();
// 'port' : process.env.PORT ||3000
app.set('port',process.env.PORT ||3000);

// 미들웨어들
// 기존 로그 외 추가적인 로그 볼 수 있음
app.use(morgan('dev'));
// 서버의 폴더 경로 : html/file.html을
// 요청 경로 : /file.html로 접근 가능
app.use('/',express.static(path.join(__dirname,'html')));
// req의 본문에 있는 데이터 해석해서 req.body 객체로 만들어줌
// 데이터가 json, url-encoded 형식일 때
// 다른 형식(text, raw)이면 추가 설치 필요
// {name='aa', book='nodejs'} => {name='aa', book='nodejs'}
app.use(express.json());
// name=aa&&book=nodejs => {name='aa', book='nodejs'}
app.use(express.urlencoded({extened:false}));
// req에 있는 쿠키를 해석해 req.cookies 객체로 만듦
// 비밀키는 .env에 저장되어 있음
app.use(cookieParser(process.env.COOKIE_SECRET));
/*
쿠키생성 및 제거 방법
res.cookie('name', 'hailie', {
    expires : new Date(Date.now()+9000),
    httpOnly : true,
    secure : true,
});
res.clearCookie('name','hailie','{httpOnly:true,secure:true});
*/
// 세션 관리용
// 세션은 사용자별로 req.session 객체 안에 유지
// 세션 관리 시 클라이언트에 쿠키 보냄
// cookie-parser의 secret과 동일한 secret 값 사용
app.use(session({
    resave: false,
    saveUninitialized: true,
    secret : process.env.COOKIE_SECRET,
    cookie : {
        httpOnly : true,
        secure : false,
    },
    name : 'session-cookie',
}))
/*
express-session으로 만들어진 req.session 객체에 
값 대입/삭제 등 변경 가능
req.session.name='hailie'; 세션 등록
req.sessionID; 세션 아이디 확인
req.session.destory(); 세션 모두 제거
*/




// GET / 라우터 : indexRouter
app.use('/', indexRouter);
// GET /user 라우터 : userRouter
app.use('/user',userRouter);
app.use('/file',fileRouter);
app.use('/login',loginRouter);
app.use('/logout',logoutRouter);
app.use('/join',joinRouter);
// app.use(경로,라우터) => 경로로 요청왔을 때 라우터 연결
// app.get(경로,라우터) => 경로로 get 요청왔을 때 라우터 연결
// app.post(경로,라우터), app.put(경로,라우터)
// app.patch(경로,라우터), app.delete(경로,라우터) ...
// app.get(키) => app.set(키,값); 했던 값


//모든 요청에 대해 다 실행
app.use((req,res,next)=>{
    res.status(404).send('Not Found');
});

app.listen(app.get('port'), ()=>{
    console.log(app.get('port'), '번 포트에서 대기 중');
})
