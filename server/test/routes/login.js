const express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');
//const { type } = require('os');
//const { reset } = require('nodemon');


const router = express.Router();

let user = {
    id : "admin",
    pwd : "admin"
};

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
    res.sendFile(path.join(__dirname,'../html/login.html'));
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
        if(req.body.id != user.id || req.body.pwd != user.pwd){
            res.send("없는 user 입니다.");
            return;
        }
        req.session.user = {
            id : req.body.id,
            login : true
        }
        req.session.save(()=>{
            console.log("로그인");
            res.send(`${req.body.id}님 환영<a href="/logout">logout</a>`);
        }); 
    }
});

module.exports=router;