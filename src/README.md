# Password Keeper

### How to use?

Java is required for this application.

* Download .JAR file from [Releases page](https://github.com/ShookTea/PasswordKeeper/releases)
* Run it!

If there is no `.password_keeper` file existing in the same directory as JAR file, it will be created when application
is launched. During that launch you will be asked for a new password that will be used to encode your data in
`.password_keeper` file.

If `.password_keeper` file exists, you will be asked for a password that you've set during first launch. If password
is correct, you'll get access to all of your data.

### How to create a backup?

Just copy the `.password_keeper` file somewhere. It is ciphered with AES algorithm and random key, while that key
is ciphered with your password. Your password should be as difficult as possible while still easy to remember for you.

### How to update an application?
Just download the new .JAR file from [Releases page](https://github.com/ShookTea/PasswordKeeper/releases) and replace
the existing one, while not touching `.password_keeper`. Application is backward compatible with all the old files,
so even if new storage format is introduced, the old ones will still be readable.

### How to collaborate?

* Take one of the existing issues from [GitHub issues page](https://github.com/ShookTea/PasswordKeeper/issues) or
    create a new one
* Write a comment with information that you want to solve that issue
* Create a fork of a repository
* Solve issue
* Create a merge request and describe what you've done!