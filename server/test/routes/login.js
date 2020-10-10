const express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');
var myspl = require('mysql');
var connection = require('../db');
//const { type } = require('os');
//const { reset } = require('nodemon');


const router = express.Router();


// test용으로 get
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
    /*
    console.log(req.body); 
    console.log(req.body.id);

    console.log("쿠키");
    console.log(req.cookies);
    
    console.log(req.session);
    console.log(req.session.user);
    */

    if(req.session.user){
        console.log("이미 로그인 되어 있음");
        code = 204;
        message = `${req.body.id}님 이미 접속 되어 있음`;
        res.json({'code':code, 'message':message});
    }
    else{
        var id = req.body.id;
        var pwd = req.body.pwd;
        var sql = 'select * from user where usr_id = ?';
        //usr_idx, usr_id, usr_pw, usr_name, usr_sex, usr_birth, usr_vegantype, usr_likeidx'

        connection.query(sql, id, function(err,result){
            if(err){
                res.json(err);
            }
            else{
                if(result.length===0){
                    code = 204;
                    message = '존재하지 않는 계정입니다.';
                    res.json({'code':code, 'message':message});
                }
                else if(pwd !== result[0].usr_pw){
                    code = 204;
                    message = '비밀번호가  틀렸습니다.';
                    res.json({'code':code, 'message':message});
                }else{
                    code = 200;
                    message = `${result[0].usr_id}님 환영합니다.`;
                    req.session.user = {
                        id : req.body.id,
                        login : true
                    }
                    req.session.save(()=>{
                        console.log("로그인");
                        res.json({'code':code, 'message':message});
                    });  
                }
            }
        });
    }
});

module.exports=router;