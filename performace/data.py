
import copy
import random


RESPONSES = [
    {
        "country": "Romania",
        "state": "Iaşi",
        "city": "Iaşi",
        "postalCode": "700028"
    },
    {
        "country": "Romania",
        "state": "Dolj",
        "city": "Craiova",
        "postalCode": "200170"
    },
    {
        "country": "Romania",
        "state": "Dâmboviţa",
        "city": "Merişoru",
        "postalCode": "137508"
    },
    {
        "country": "Romania",
        "state": "Dâmboviţa",
        "city": "Pucioasa",
        "postalCode": "135400"
    },
    {
        "country": "France",
        "state": "Normandie",
        "city": "Yquelon",
        "postalCode": "50400"
    },
    {
        "country": "France",
        "state": "Normandie",
        "city": "Morigny",
        "postalCode": "50410"
    },
    {
        "country": "Canada",
        "state": "Saskatchewan",
        "city": "Yorkton",
        "postalCode": "S3N"
    },
]

BODIES = [
]

def destroyString(x):
    alphabet = string.ascii_lowercase + string.ascii_uppercase
    return ''.join(i if random.random() < 0.9 else random.choice(alphabet) for i in x)


for _ in range(1000):
    res = copy.deepcopy(random.choice(RESPONSES))
    req = copy.deepcopy(res)

    req['country'] = destroyString(req['country'])
    req['state'] = destroyString(req['state'])
    req['city'] = destroyString(req['city'])
    req['postalCode'] = destroyString(req['postalCode'])
    body = (req, res)
    BODIES.append(body)


