#include <iostream>
#include <string>

class Cat
{
    public:
        Cat(std::string a_name, std::string a_breed, int a_age){this->name = a_name; this->breed = a_breed; this->age = a_age;}

        std::string getBreed(){return this->breed;}
        int getAge(){return this->age;}
        std::string getName(){return this->name;}

        void setBreed(std::string breed){this->breed = breed;}
        void setAge(int age){this->age = age;}
        void setName(std::string name){this->name = name;}
    private:
        std::string breed = "";
        int age = 0;
        std::string name = "";
};

class IRLCat : Cat
{
    IRLCat::Cat cat();
    //IRLCat(std::string a_name, std::string a_breed, int a_age){this->name = a_name; this->breed = a_breed; this->age = a_age;}

    public:
        bool getHungry(){return this->isHungry;}
        void setHungry(bool value){this->isHungry = value;}

    private:
        bool isHungry = false;
};

class DigitalCat : Cat
{
    //DigitalCat(std::string a_name, std::string a_breed, int a_age){this->name = a_name; this->breed = a_breed; this->age = a_age;}

    public:
        int getRamConsumed(){return this->ramConsumed;}
        void setRamConsumed(int value){this->ramConsumed = value;}

    private:
        int ramConsumed = 0;
};

class MurderousDigitalCat : DigitalCat
{
    //MurderousDigitalCat(std::string a_name, std::string a_breed, int a_age){this->name = a_name; this->breed = a_breed; this->age = a_age;}

    public:
        void murderTarget(std::string target){std::cout << "Murdered target: " << target << std::endl;}
    
    private:
        double attack = 10.0;
        double def = 20.0;
};

int main()
{
    std::cout << "Hello, world!" << std::endl;
    Cat cat;
        cat.setName("cat");
        cat.setBreed("cat");
        cat.setAge(10);
    
}