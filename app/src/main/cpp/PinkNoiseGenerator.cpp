#pragma once
#include "PinkNoiseGenerator.h"
#include <math.h>
#include <random>


namespace synth{

    PinkNoiseGenerator::PinkNoiseGenerator(){
        generator = std::mt19937(std::random_device{}());
    }

    void PinkNoiseGenerator::wrap(int M, int &q) {
        if (q > M) q -= (M + 1);
        if (q < 0) q += (M + 1);
    }

    void PinkNoiseGenerator::cdelay(int D, int &q) {
        --q;
        wrap(D, q);
    }

    double PinkNoiseGenerator::generateRandomNumber() {
        return dist(generator);
    }

    double PinkNoiseGenerator::ranh(int D, double &u, int &q) {
        double y = u;
        cdelay(D - 1, q);
        if (q == 0) {
            u = generateRandomNumber();
        }
        return y;
    }

    float PinkNoiseGenerator::getSample() {
        double y = 0;
        for(int b =0 ; b<bands; b++){
            y += ranh(1 << b, randomValues[b], circularIndex[b]);
        }
        return static_cast<float>(0.5*y/bands);
    }
}