var express = require('express')
  , http = require('http');
var static = require('node-static');
var app = express(),
  server = http.createServer(app);
var passport = require('passport');
require('./config/passport')(passport);
var flash = require('connect-flash');
var cookieParser = require('cookie-parser');
var session = require('express-session');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');
var winston = require('winston');
var Yelp = require('yelp');
var configDB = require('./config/database.js');
mongoose.connect(configDB.url);

var handlebars = require('express3-handlebars').create({defaultLayout:'main'});

winston.emitErrs = true;

var logger = new winston.Logger({
    transports: [
        new winston.transports.File({
            level: 'info',
            filename: './logs/yelp-logs.log',
            handleExceptions: true,
            json: true,
            maxsize: 5242880, //5MB
            maxFiles: 5,
            colorize: false
        }),
        new winston.transports.Console({
            level: 'debug',
            handleExceptions: true,
            json: false,
            colorize: true
        })
    ],
    exitOnError: false
});

app.engine('handlebars', handlebars.engine);
app.set('view engine', 'handlebars');
app.use(cookieParser());
app.use(bodyParser.urlencoded({extended: false}));


app.use(passport.initialize());
app.use(passport.session()); // persistent login sessions
app.use(flash()); // use connect-flash for flash messages stored in session
app.use(express.static(__dirname + '/public'));

require('./routes/routes.js')(app, passport);

app.use(function(req,res,next){
  //res.type('text/plain');
  res.status('404');
  res.render('404');
});

server.listen(8080, function () {  
	logger.info('A user connected!');
});




