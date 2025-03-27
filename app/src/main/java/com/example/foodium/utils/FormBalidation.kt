package com.example.foodium.utils

fun validateUsername(username:String):String{
    return if(username.trim().isEmpty()){
        "username is required ,enter username"
    }else if(username.trim()=="admin"){
        "admin username is forbidden,enter another one"
    }
    else{
        ""
    }
}

fun validateEmail(email:String):String{
    val emailPattern = Regex("[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+")
    return if(email.trim().isEmpty()){
        "email is required ,enter email"
    }else if(!email.trim().matches(emailPattern)){
        "invalid email,enter a valid one"
    }else{
        ""
    }
}

fun validatePassword(password:String):String{
//    val passwordPattern=Regex("/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$/")
    val passwordRegex = Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d)(?=.*?[#?!@\$%^&*.-]).{8,}$")
    return if(password.trim().isEmpty()){
        "password is required,enter it"
    }else if(!password.trim().matches(passwordRegex)){
        "weak password,password must have 8 charaacters long,have an uppercas,lowercase,digit,special character"
    }else{
        ""
    }
}

fun validateConfirmPassword(password:String,confirmPassword:String):String{
    return if(confirmPassword.trim().isEmpty()){
        "confirm password is required,enter it"
    }else if(confirmPassword.trim()!=password.trim()){
        "confirm password must match password"
    }else{
        ""
    }

}
fun validateWeight(weightStr:String):String{
    try {
        val weight=weightStr.toInt()
        return if(weightStr.trim().isEmpty()){
            "weight is blank enter one"
        } else if(weight==0){
            "zero is not valid,are you a photon?"
        }else if(weight>635){
            "weight is above 635 not allowed,did you just break the guinness world record for heaviest person"
        }else if(weight<0){
            "negative weights not allowed,are you made of anti-matter?"
        }else{
            ""
        }
    }catch (e:Exception){
        return  "invalid weight,only numbers are allowed"
    }


}

fun validateNofMeals(noOfMealsStr:String):String{
    try {
        val noOfMeals=noOfMealsStr.toInt()
        return if(noOfMeals==0){
            "zero is not valid Mahatma Gandhi"
        }else if(noOfMeals>100){
            "number above 100,slow down champ"
        }else{
            ""
        }

    }catch (e:Exception){
        return "invalid no of meals,only numbers allowed"

    }

}