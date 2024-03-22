#include <iostream>
#include <string>

class Cat
{
    public:
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
    
};

class DigitalCat : Cat
{

};

class MurderousDigitalCat : DigitalCat
{
    
};