#!/bin/bash

INPUT_CSS_PATH=".\src\main\resources\static\css\input.css"
OUTPUT_CSS_PATH=".\src\main\resources\static\css\tailwind.css"

npx tailwindcss -i $INPUT_CSS_PATH -o $OUTPUT_CSS_PATH