#!/usr/bin/env sh

if ! [ -x "$(command -v brew)" ]; then
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
fi

if ! [ -x "$(command -v mysql)" ]; then
    brew install mysql
    brew services start mysql
    mysqladmin -u root password 'password'
fi

