

function convertUnixTimeStampToString(toConvert){
    // console.log("Time to convert: ",toConvert);
    let newDate = new Date(toConvert).toLocaleString();
    return newDate;
}

function createUnixTimeStamp(toConvert){
    if(!!toConvert){
        return Date.parse(new Date(toConvert))
    }
    return Date.parse(new Date());
}


export { convertUnixTimeStampToString };