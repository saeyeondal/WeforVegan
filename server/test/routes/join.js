const express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');
var myspl = require('mysql');
var connection = require('../db');

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
    res.sendFile(path.join(__dirname,'../html/join.html'));
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
        id = req.body.id;
        pwd = req.body.pwd;
        name = req.body.name;
        if(req.body.sex=='여자')
            sex = 'F';
        else
            sex = 'M';
        birth = req.body.birth;
        vegantype = req.body.vegantype;

        var sql = 'INSERT INTO user (usr_id, usr_pw, usr_name, usr_sex, usr_birth, usr_vegantype) VALUES(?, ?, ?, ?, ?, ?)'
        //usr_idx, usr_id, usr_pw, usr_name, usr_sex, usr_birth, usr_vegantype, usr_likeidx
        var params = [id, pwd, name, sex, birth, vegantype];
        // db에 삽입
        connection.query(sql,params,function(err, result){
            if(err){
                console.log(err);
                if(err.code=="ER_DUP_ENTRY")
                    message = '아이디 중복';
                else
                    message = err;
                res.json({'code':204, 'message':message});
            }else{
                resultCode=200;
                code = 200;
                message = `회원가입 성공`;
                res.json({'code':code, 'message':message});
            }
        });
    }
});

module.exports=router;