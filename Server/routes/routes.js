var Yelp = require("yelp");
var User = require('../models/user');
var configAuth = require('../config/auth');
var winston = require('winston');
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

module.exports = function(app, passport){
	app.get('/', function(req, res){
		logger.info('User get to index page');
		res.render('home');
	});

	app.get('/test', function(req, res){
		logger.info('User get to test page');
		var testRes= '{"test":"result of test"}';
		res.json(JSON.parse(testRes));
	});

	//User Management API
	app.get('/api/user/:userId',function(req, res){
		logger.info('Getting information of user'+req.params.userId);
		User.findOne({'facebook.id':req.params.userId}, function(err, user) {
            if (err)
                res.send(err);
            res.json(user);
        });

	});



	//Yelp API
	app.get('/api/yelp/nearBy/:radius',function(req, res){
		var yelp = new Yelp({
		  consumer_key: configAuth.yelp.consumer_key,
		  consumer_secret: configAuth.yelp.consumer_secret,
		  token: configAuth.yelp.token,
		  token_secret: configAuth.yelp.token_secret,
		});
		
		yelp.search({ term: 'chinese food', radius_filter: req.params.radius, location:'Boston', limit:'10' })
		.then(function (data) {
			 console.error(data);
		  res.json({yelpSearchNearByResponse:{data}});
		})
		.catch(function (err) {
		  console.error(err);
		  res.json({yelpSearchNearByResponse:{err}});
		});

	});



	app.get('/auth/facebook', passport.authenticate('facebook', {scope: ['email']}));

	app.get('/auth/facebook/callback', function(req, res, next) {
	  passport.authenticate('facebook', function(err, user, info) {
	    if (err) { return next(err); }
	    if (!user) { return res.redirect('/'); }
	    req.logIn(user, function(err) {
	      if (err) { return next(err); }
	      return res.redirect('/api/user/' + user.facebook.id);
	    });
	  })(req, res, next);
	});


	app.get('/logout', function(req, res){
		req.logout();
		res.redirect('/');
	})
};
