#include <iostream>
#include <string>

class Cat
{
    public:
        std::string getBreed(){return this->breed;}
        int getAge(){return this->age;}
        std::string getName(){return this->name;}
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