#include <stdio.h>
#include <string.h>
void fun(char *s) {
    printf("User: %s\n", s);
    char all_data[] = "Chatbot: Hi there!,There is a T20 match happening today.,I can tell you about the latest cricket scores.,Let me know if you need information on cricket teams or players.,The weather today is perfect for cricket.,The cricket stadium is located in the city center.,";
    char *arr[6];
    char *token = strtok(all_data, ",");
    int i = 0;
    while (token != NULL) {
        arr[i++] = token;
        token = strtok(NULL, ",");
    }
    if (strcmp(s, "hello") == 0) {
        printf("Chatbot: Hi there Ready to talk cricket\n");
    } else if (strcmp(s, "what happening today") == 0) {
        printf("Chatbot: There is a T20 match happening today\n");
    } else if (strcmp(s, "tell me  cricket score") == 0) {
        printf("Chatbot: I can tell you the latest cricket score. \n");
    } else if (strcmp(s, "information on cricket teams") == 0) {
        printf("Chatbot: Let me know if you need information on cricket teams.\n");
    } else if (strcmp(s, "what is the weather like for cricket") == 0) {
        printf("Chatbot: The weather today is perfect for cricket.\n");
    } else if (strcmp(s, "where is the cricket stadium located") == 0) {
        printf("Chatbot: The cricket stadium is located in the city bengluru.\n");
    } else {
        printf("Chatbot: I'm sorry,  Can you please repeat or ask something cricket-related?\n");
    }
}
int main() {
    char s[100];
    printf("Welcome to Cricket Chatbot\n");
    while (1) {
        printf("> ");
        fgets(s, sizeof(s), stdin);
        s[strcspn(s, "\n")] = '\0'; 

        if (strcmp(s, "exit") == 0) {
            printf("Thank You!\n");
            break;
        }
        fun(s);
    }
    return 0;
}
