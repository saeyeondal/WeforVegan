const express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var expressSession = require('express-session');
var myspl = require('mysql');
var connection = require('../db');

const router = express.Router();

// 좋아요 등록
router.post('/add',(req,res)=>{
    // /heart/add 를 post방식으로 보낸다면
    // body에는 rp_idx가 들어있음

    // 로그인 되어 있다면
    if(req.session.user){
        // userDB에서 해당 user 찾아서 좋아요 정보 가져옴
        var sql = 'select usr_likeidx from user where usr_id = ?';
        //usr_idx, usr_id, usr_pw, usr_name, usr_sex, usr_birth, usr_vegantype, usr_likeidx
        var id = req.session.user.id;
        connection.query(sql, id, function(err, result){
            if(err){
                res.json(err);
            }
            else{
                if(result.length===0){
                    code = 204;
                    message = '존재하지 않는 계정입니다.';
                    res.json({'code':code, 'message':message});
                }
                else{
                    rp_idx_like = reseult[0].usr_likeidx;
                    rp_idx_like = rp_idx_like + req.body.rp_idx + ",";
                    var updateSQL = 'UPDATE user SET usr_likeidx=? WHERE usr_id=?';
                    connection.query(updateSQL,rp_idx_like,function(err, result){
                        // 좋아요정보가져온거+req에 있는거 해서 userDB UPDATE
                        if(err){
                            res.json(err);
                        }
                        else{
                            if(result.length===0){
                                code = 204;
                                message = '존재하지 않는 계정입니다.';
                                res.json({'code':code, 'message':message});
                            }
                            else{
                                code = 200;
                                message = '수정 완료';
                                res.json({'code':code, 'message':message});
                            }
                        }
                    });                       
                }  
            }              
        });
    }
    else {
        // 로그인이 안되어 있다면
        console.log("로그인 안 되어 있음");
        code = 204;
        message = `비회원입니다.`;
        res.json({'code':code, 'message':message});
    }
});


// 좋아요 삭제
router.post('/cancle',(req,res)=>{
    // /heart/cancle 를 post방식으로 보낸다면
    // body에는 rp_idx가 들어있음

    // 로그인 되어 있다면
    if(req.session.user){
        // userDB에서 해당 user 찾아서 좋아요 정보 가져옴
        var sql = 'select usr_likeidx from user where usr_id = ?';
        //usr_idx, usr_id, usr_pw, usr_name, usr_sex, usr_birth, usr_vegantype, usr_likeidx
        var id = req.session.user.id;
        connection.query(sql, id, function(err, result){
            if(err){
                res.json(err);
            }
            else{
                if(result.length===0){
                    code = 204;
                    message = '존재하지 않는 계정입니다.';
                    res.json({'code':code, 'message':message});
                }
                else{
                    rp_idx_like = reseult[0].usr_likeidx;
                    rp_idx_like = rp_idx_like + req.body.rp_idx + ",";
                    var updateSQL = 'UPDATE user SET usr_likeidx=? WHERE usr_id=?';
                    connection.query(updateSQL,rp_idx_like,function(err, result){
                        // 좋아요정보가져온거+req에 있는거 해서 userDB UPDATE
                        if(err){
                            res.json(err);
                        }
                        else{
                            if(result.length===0){
                                code = 204;
                                message = '존재하지 않는 계정입니다.';
                                res.json({'code':code, 'message':message});
                            }
                            else{
                                code = 200;
                                message = '수정 완료';
                                res.json({'code':code, 'message':message});
                            }
                        }
                    });                       
                }  
            }              
        });
    }
    else {
        // 로그인이 안되어 있다면
        console.log("로그인 안 되어 있음");
        code = 204;
        message = `비회원입니다.`;
        res.json({'code':code, 'message':message});
    }
});

// 좋아요 테스트
router.get('/', (req,res)=>{
    console.log(req.session);
    if(!req.session.user){
        res.send(`로그인 먼저 하세요.`);
    }
    else
        res.sendFile(path.join(__dirname,'../html/heart.html'));
});


module.exports=router;