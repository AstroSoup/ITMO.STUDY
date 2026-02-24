#include <stdlib.h>
#include <stdio.h>
#include <memory.h>

typedef struct {
    unsigned char day;
    unsigned char month;
    unsigned long year;
} Date;

typedef enum {
    MALE,
    FEMALE
} GENDERS;

typedef struct {
    char* name;
    char* group;
    GENDERS gender;
    Date birthday;
} Student;

Student* create_student(char* name, char* group, GENDERS gender, Date birthday) {
    Student* student = (Student*)malloc(sizeof(Student));

    if (!student) {
        fprintf(stderr, "Memory allocation for student failed.");
        return NULL;
    }

    student->name = name;
    student->group = group;
    student->birthday = birthday;
    student->gender = gender;
    
    return student;
}

Date* create_date(unsigned char day, unsigned char month, unsigned long year) {
    Date* date = (Date*)malloc(sizeof(Date));

    if(!date) {
        fprintf(stderr, "Memory allocation for date failed.");
        return NULL;
    }

    date->day = day;
    date->month = month;
    date->year = year;

    return date;
}

char* gender_to_string(GENDERS gender) {
    switch(gender) {
        case MALE: return "male";
        case FEMALE: return "female";
        default: return "unknown";
    }
}

void print_date(const Date* date, FILE* fd) {
    
    if(!date) {
        fprintf(stderr, "Date isn`t initialized.");
    }

    if (fd == stdout || fd == stderr) {
        fprintf(fd ,"%hhu.%hhu.%lu\n", date->day, date->month, date->year);
    } else {
        fwrite(date, sizeof(*date), 1, fd);
    }

}

void print_student(Student* student, FILE* fd) {
    if(!student) {
        fprintf(stderr, "Student isn`t initialized.");
    }

    
    if (fd == stdout || fd == stderr) {
        fprintf(fd, "name: %s\n", student->name);
        fprintf(fd, "group: %s\n", student->group);
        fprintf(fd, "date: ");
        print_date(&student->birthday, fd);
        fprintf(fd, "gender: %s\n", gender_to_string(student->gender));
    } else {
        fwrite(student, sizeof(*student), 1, fd);
    }
}

Student* scan_student(FILE* fd) {
    if (!fd) {
        fprintf(stderr, "Failed to open fd for reading\n");
        return NULL;
    }

    Student* student = (Student*)malloc(sizeof(Student));

    if (!student) {
        fprintf(stderr, "Memory allocation for student failed.");
        return NULL;
    }

    fread(student, sizeof(*student), 1, fd);

    return student;
}

int main() {
    Student* student = create_student("Gleb Taustob", "P3208", MALE, *create_date(28,1,2006));

    FILE* file = fopen("student.dat", "wb");

    print_student(student, file);
    fclose(file);
    file = fopen("student.dat", "rb");

    print_student(scan_student(file), stdout);
}