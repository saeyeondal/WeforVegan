const express = require('express');
const path = require('path');
const router = express.Router();

router.get('/',(req,res) => {
    console.log(__dirname);
    res.sendFile(path.join(__dirname,'../html/file.html'));
});

module.exports=router;