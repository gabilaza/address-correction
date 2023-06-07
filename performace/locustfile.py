
import config
import data
from random import choice
from locust import HttpUser, task, between

class Performance(HttpUser):

    @task
    def performance(self):
        self.client.post(config.API_PATH, json=choice(data.BODIES)[0])

    @task
    def precision(self):
        corrects = 0
        for req, res_exp in data.BODIES:
            res = self.client.post(config.API_PATH, json=req).json()
            if res == res_exp:
                corrects += 1
            # else:
            #     print(req)
            #     print(res)
            #     print(res_exp)
            #     print()

        print(corrects/len(data.BODIES))


