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
        code = 200;
        message = `로그아웃 성공`;
        res.json({'code':code, 'message':message});
    }
    else{
        code = 204;
        message = `로그인 안되어 있음`;
        res.json({'code':code, 'message':message});
    }
});

module.exports=router;