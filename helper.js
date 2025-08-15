const fs = require('fs');
const path = require('path');
const { exec } = require('child_process');


function getAllFiles(dirPath, arrayOfFiles = []) {
  const metadataFolders = ['.git', '.venv', 'node_modules', '.idea', '.vscode', "target"];
  const files = fs.readdirSync(dirPath);

  files.forEach(file => {
    // Skip metadata folders and files
    if (metadataFolders.includes(file)) return;
    if (file.endsWith('.class')) return; // Ignore .class files
    if (file.endsWith('.tokens')) return; // Ignore .log files
    if (file.endsWith('.interp')) return; // Ignore .log files

    const fullPath = path.join(dirPath, file);
    if (fs.statSync(fullPath).isDirectory()) {
      arrayOfFiles = getAllFiles(fullPath, arrayOfFiles);
    } else {
      arrayOfFiles.push(fullPath);
    }
  });

  return arrayOfFiles;
}



function copyToClipboard(text, fileList) {
  // Detect OS and use appropriate clipboard command
  const platform = process.platform;
  let command;

  if (platform === 'darwin') {
    command = 'pbcopy';
  } else if (platform === 'win32') {
    command = 'clip';
  } else {
    command = 'xclip -selection clipboard';
  }

  const child = exec(command, (error) => {
    if (error) {
      console.error('❌ Error copying to clipboard:', error);
      console.log('\n--- Content that would be copied ---');
      console.log(text);
    } else {
      console.log('✅ Source files copied to clipboard!');
      console.log('\n📁 Files copied:');
      fileList.forEach((file, index) => {
        console.log(`  ${index + 1}. ${file}`);
      });
      console.log(`\n📊 Total: ${fileList.length} file(s)`);
    }
  });

  child.stdin.write(text);
  child.stdin.end();
}

function main() {
  try {
    const targetDirs = process.argv.slice(2);
    if (targetDirs.length === 0) {
      console.log('Usage: node helper.js <folder1> <folder2> ...');
      process.exit(1);
    }
    // Resolve each dir relative to the script's directory
    const dirsToProcess = targetDirs.map(dir => path.resolve(__dirname, dir));
    let allFiles = [];

    dirsToProcess.forEach(dir => {
      const files = getAllFiles(dir);
      allFiles = allFiles.concat(files);
    });

    // Remove duplicates (in case of overlapping folders)
    allFiles = Array.from(new Set(allFiles));

    let output = '';

    console.log('🔄 Processing files...');

    allFiles.forEach(filePath => {
      const content = fs.readFileSync(filePath, 'utf8');
      output += `File: ${filePath}\n`;
      output += `${content}\n\n`;
    });

    copyToClipboard(output, allFiles);
  } catch (error) {
    console.error('❌ Error reading files:', error);
  }
}


main();