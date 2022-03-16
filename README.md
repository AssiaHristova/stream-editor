stream-editor is a Spring Boot API that edit text from a file called input.txt, located in the resource folder.
You can run the program in you favourite java IDE or build it in a container. To do so, please open a terminal in the project folder and type the command:
docker build -t stream-editor .
Wait until the image gets built and run it with the command:
docker run -dp 8080:8080


Now you can open localhost:8080/s/hello/world/ and it will substitute all occurrences of 'hello' with 'world'.
Of course, you may try with different words - just open the folder resources and type any text in the input.txt file. 
Then go to localhost:8080/s/word1/word2/ and it will substitute word1 with word2.
The result will be printed on the output.txt file.
Please keep in mind that it is case-sensitive, so if you want to replace the word 'Hello', you should type it with capital letters.

If you go to localhost:8080/i/s/hello/world/, the stream editor will substitute it on the same input.txt file.

The project also have OpenAPI integrated(Swagger) for easier access to the API endpoints.

It can be accessed at: http://localhost:8080/swagger-ui/index.html, where you can try with different words (first click on Try it out button).