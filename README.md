# midara

A very simple CI server.

# Warning

I used it to build noty.im infrastructure. However, since it runs shell, it's
best to run Midara in a private, heavily firewall network. You
should also whitelist who can trigger build to prevent anyone run
arbitrary command

# Why

Tired of using YAML file in CI. This CI bring back the power of bash. Yes, you
write bash script to run build/deploy. The CI, however, exposed a set of bash
function you can use.

When receiving a web hook, Midara gather information about the commit (repo,
owner, user, commit, branch, pr...), create a workspace, checkout your code
into that workspace, run a docker container, source your build script with `source .midara`. and invoke `main` function.


# Shell structure:

Create a file call `.midara` with a `main` function. Your code is in
`/workspace`.

# Build process

The build is kicked off by run a docker container. Then

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    $ java -jar midara-0.1.0-standalone.jar [args]

## Directory structure

- username
    * repo
      * code
      * jobs
        * job-id
          - log


## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2016 NotyIM

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
