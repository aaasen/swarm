#!/bin/sh

# This script removes newlines from all files passed in
# remove_newlines file_1 file_2 file_3 ...

for path in "$@"
do
  echo $(tr -d '\n' < $path) > $path
done

