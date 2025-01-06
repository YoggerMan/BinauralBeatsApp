#pragma once
#include <vector>
#include <random>



namespace synth {
    class PinkNoiseGenerator{
    public:

        PinkNoiseGenerator();

        double generateRandomNumber();
        float getSample();
        void cdelay(int D, int &q);
        void wrap(int M, int &q);
        double ranh(int D, double &u, int &q);



    private:
        std::mt19937 generator;
        std::uniform_real_distribution<double> dist = std::uniform_real_distribution<double>(-1,1);
        int bands = 16;
        std::vector<double> randomValues = std::vector<double>(bands, 0);
        std::vector<int> circularIndex = std::vector<int>(bands, 0);
    };
}