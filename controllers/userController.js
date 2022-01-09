const Subscriber = require();

exports.getAllUsers = (req, res, next) => {
    User.find({}, (error, users) => {
        if (error) next(error);
        req.data = users;
        next();
    });
};