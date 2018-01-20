import pandas
import pickle
from sklearn import preprocessing

#%%    Load the model from disk
loaded_model = pickle.load(open('G:/College/Academics/Semester VII/NS/Project/Honeypot_NSProject/src/Resources/finalized_model.sav', 'rb'))  
#%%    Read input file and preprocess data

url = 'G:\College\Academics\Semester VII\NS\Project\Honeypot_NSProject\src\Resources\Machine_Python.csv'
names = ['duration','protocol_type','service','flag','src_bytes','dst_bytes','land','wrong_fragment','urgent']

dataframe = pandas.read_csv(url, names=names)       #Reads csv and returns as a DataFrame

array = dataframe.values                            #Convert DataFrame to numpy.ndarray
X = array[:,:]                                     #Input vector

#Encode string features to integers
protocolE=preprocessing.LabelEncoder()
X[:,1]=protocolE.fit_transform(X[:,1])

serviceE=preprocessing.LabelEncoder()
X[:,2]=serviceE.fit_transform(X[:,2])

flagE=preprocessing.LabelEncoder()
X[:,3]=flagE.fit_transform(X[:,3])

#%%     Encodings for labels
mapping={
    11:'Normal',
    0: 'Back',
    1: 'Buffer Overflow',
    2: 'FTP Write',
    3: 'Guess Password',
    4: 'IMAP',
    5: 'IPsweep Probe',
    6: 'Land',
    7: 'Loadmodule',
    8: 'Multihop',
    9: 'Neptune',
    10:'Nmap Probe',
    12:'Perl',
    13:'PHF',
    14:'Pod',
    15:'portsweep probe',
    16:'Rootkit',
    17:'Satan Probe',
    18:'Smurf',
    19:'Spy',
    20:'Teardrop',
    21:'Warezclient',
    22:'Warezmaster'
}
attack_group='N/A'
attack_type='N/A'

#%%    Predict result
result=loaded_model.predict(X[0])                   #Saved as numpy array

#Convert result to words
if result == 11:
    status='false'	#No attack
else:
    status='true'	#Attack
    
attack_type = mapping[result[0]]
if result in [0,6,9,14,18,20]:
    attack_group='Denial of Service'
if result in [2,3,4,8,13,19,21,22]:
    attack_group='Unauthorized Access from a Remote Machine'
if result in [1,7,12,16]:
    attack_group='Unauthorized Access to Local root Privileges'
if result in [5,10,15,17]:
    attack_group='Probe'
    
#%%     Write result to file
import csv
f = open('G:\College\Academics\Semester VII\NS\Project\Honeypot_NSProject\src\Resources\ML_Output.txt', 'wt')
try:
    writer = csv.writer(f)
    writer.writerow( (status, attack_group, attack_type) )
finally:
    f.close()