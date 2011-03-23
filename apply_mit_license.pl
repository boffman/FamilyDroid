#!/usr/bin/perl -w
#
# Applies MIT license to all .java files from current directory
#
use strict;

my $license = sprintf << 'EOF';
/*
Copyright (c) 2011 Team FK-Appening

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
EOF

sub has_license {
    my $filename = shift;
    die("Unknown file: $filename") unless (-f $filename);
    my $secondline = `head -2 $filename | tail -1`;
    return ($secondline && $secondline =~ /Copyright.+Appening/);
}

sub apply_license {
    my $filename = shift;
    my $fh;
    open($fh, "<$filename") or die("Failed to open $filename for reading!");
    my @lines = <$fh>;
    close($fh) or die("Failed to close $filename after reading!");
    open($fh, ">$filename") or die("Failed to open $filename for writing!");
    print {$fh} $license;
    foreach my $line (@lines) {
        print {$fh} $line;
    }
    close($fh) or die("Failed to close $filename after writing!");
}

my @filenames = `find . -name "*.java"`;
foreach my $filename (@filenames) {
    chomp($filename);
    unless(has_license($filename)) {
        apply_license($filename);
        print "Applied license to $filename\n";
    }
}

print "Done.\n";

