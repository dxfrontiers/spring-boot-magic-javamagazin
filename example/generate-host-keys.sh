#!/usr/bin/env sh

######
## Utility script to generate server keys
######

ssh-keygen -f ./src/main/resources/ssh_host_rsa_key -N '' -t rsa -C "sshd example"
ssh-keygen -f ./src/main/resources/ssh_host_dsa_key -N '' -t dsa -C "sshd example"

echo ""
echo "Host keys generated. Fingerprints:"
echo ""
ssh-keygen -lf ./src/main/resources/ssh_host_rsa_key
ssh-keygen -lf ./src/main/resources/ssh_host_dsa_key
