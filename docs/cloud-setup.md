# Create user
- `sudo useradd wiki-edit-analyze`

# create priv/pub keys
- put public key into allowed keys: `/home/wiki-edit-analyzer/.ssh/authorized_keys`
- `ssh-keygen -t rsa -b 4096 -C "wiki-edit-analyzer@fischo.sk"`
- use private key in github secrets (with all content)

# install docker on linux

## AlmaLinux
- `sudo dnf config-manager --add-repo=https://download.docker.com/linux/centos/docker-ce.repo`
- `sudo dnf install docker-ce docker-ce-cli containerd.io`
- `sudo systemctl start docker`

### Manage Docker as a non-root user
- `sudo groupadd docker`
- `sudo usermod -aG docker wiki-edit-analyzer`

### Configure Docker to start on boot
- `sudo systemctl enable docker`

### install docker compose
- `sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose`
- `sudo chmod +x /usr/local/bin/docker-compose`
- Fix issue with docker compose `sudo dnf install libxcrypt-compat`