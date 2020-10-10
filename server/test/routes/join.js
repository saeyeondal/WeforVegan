const express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');

const router = express.Router();


router.get('/',(req,res)=>{
    console.log(req.session);
    if(req.session.user){
        console.log("쿠키");
        console.log(req.cookies);
        console.log(req.session);
        console.log(req.session.id);
        console.log("이미 로그인 되어 있음");
        res.send(`${req.session.user.id}님 이미 접속 되어 있음.<a href="/logout">logout</a>`);
        return;
    }
    res.sendFile(path.join(__dirname,'../html/join.html'));
});

router.post('/',(req,res) => 
{
    console.log(req.body); 
    console.log(req.body.id);

    console.log("쿠키");
    console.log(req.cookies);
    
    console.log(req.session);
    console.log(req.session.user);

    if(req.session.user){
        console.log("이미 로그인 되어 있음");
        res.send(`${req.body.id}님 이미 접속 되어 있음.<a href="/logout">logout</a>`);
    }
    else{
        // db에 저장하기
        res.send(req.body);
    }
});

module.exports=router;