

function convertUnixTimeStampToString(toConvert){
    console.log("Time to convert: ",toConvert);
    let newDate = new Date(toConvert).toLocaleString();
    return newDate;
}


export { convertUnixTimeStampToString };