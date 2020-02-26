# Create Temp File.
# Call createTmp, then the path of temp file is stored in the variable "tmpFile".
# The temp file will be removed automatically when the shell exit.

tmpAry=()
tmpFile=""

function createTmp(){
    tmpFile=$(mktemp)
    tmpAry+=( $tmpFile ) 
}

function rm_tmpfile(){
    for ((i = 0; i < ${#tmpAry[@]}; i++)) {
        tmpFile=${tmpAry[i]}
        if [[ -f "$tmpFile" ]]
        then
          # echo "delete $tmpFile"
          rm -f "$tmpFile"
        fi
    }
}

trap rm_tmpfile EXIT
