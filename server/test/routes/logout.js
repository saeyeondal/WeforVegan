const express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');


const router = express.Router();

router.get('/',(req,res) => {
    if(req.session.user){
        console.log("로그아웃");
        req.session.destroy();
        console.log('세션 삭제 성공');
        res.send("로그아웃 성공");
        /*req.session.destroy(
            function (err) {
                if (err) {
                    console.log('세션 삭제시 에러');
                    return;
                }
                console.log('세션 삭제 성공');
                res.send("로그아웃 성공");
            }
        );*/    //세션정보 삭제
    }
    else{
        console.log("로그인 안 되어 있음");
        res.redirect('../login');
    }
});

module.exports=router;